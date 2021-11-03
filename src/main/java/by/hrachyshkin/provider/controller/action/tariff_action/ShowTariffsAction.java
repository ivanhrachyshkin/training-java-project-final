package by.hrachyshkin.provider.controller.action.tariff_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tariffs")
public class ShowTariffsAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

        final String rawType = request.getParameter("filter");
        final List<Tariff> tariffs;

        if (rawType == null || rawType.equals("all")) {
            tariffs = tariffService.find();
        } else {
            final Tariff.Type type = Tariff.Type.valueOf(rawType.toUpperCase());
            tariffs = tariffService.findAndFilterByType(type);
        }

        request.setAttribute("tariffs", tariffs);

        if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
            request.getRequestDispatcher("/all-tariffs-for-admin.jsp").forward(request, response);
        } else {
            final List<Tariff> accountTariffs = tariffService.findTariffsForSubscription(getAccountId(request));
            request.setAttribute("accountTariffs", accountTariffs);
            request.getRequestDispatcher("/all-tariffs-for-user.jsp").forward(request, response);
        }

    }
}
