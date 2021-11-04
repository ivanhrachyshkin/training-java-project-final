package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowBillsForSubscriptionAction extends BaseAction {

    public static final String SHOW_BILLS_FOR_SUBSCRIPTION = "/cabinet/subscriptions/bills";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            checkHttpMethod(request);

            final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL_SERVICE);
            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final int offset = getOffset(request);
            final Integer accountId = getAccountId(request);
            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            final Tariff tariff = tariffService.findOneById(tariffId);
            final Account account = accountService.findOneById(accountId);
            final List<Bill> subscriptionBills = billService.findBillsForSubscription(accountId, tariffId, offset);

            setPage(request);
            setTotalPagesAttribute(request, subscriptionBills);
            request.setAttribute("tariff", tariff);
            request.setAttribute("account", account);
            request.setAttribute("subscriptionBills", subscriptionBills);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/bills-for-subscription.jsp";
    }
}
