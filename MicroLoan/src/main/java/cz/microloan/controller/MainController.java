package cz.microloan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.microloan.component.SystemPropertyResolver;
import cz.microloan.entity.Loan;
import cz.microloan.model.LoanDetailView;
import cz.microloan.service.LoanService;

/**
 * @author Jan Tolar
 */
@Controller
public class MainController {

	@Autowired
	SystemPropertyResolver systemPropertyResolver;
	
	@Autowired
	LoanService loanService;

	/**
	 * Set initial data for form page and return it
	 * @param Model model
	 * @return loan_form main page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final Model model) {
		
		// Add possible min and max loan terms for loan_form term select element
		model.addAttribute("minimumTermDays", systemPropertyResolver.getIntProperty("min.term.days.Number"));
		model.addAttribute("maximumTermDays", systemPropertyResolver.getIntProperty("max.term.days.Number"));
		
		// Add possible loan amounts for loan_form amount select element
		model.addAttribute("amountSelectValues", setupLoanAmountSelect());

		return "loan_form";
	}

	/**
	 * @param String uuid from request for load Loan from db
	 * @param Model model
	 * @return loan_detail as a detail of loan page
	 */
	@RequestMapping(value = "/loan_detail", method = RequestMethod.GET)
	public String loanDetail(
			@RequestParam("uuid") final String uuid, 
			final Model model) {
		
		final Loan loan = loanService.findByUuid(uuid);
		
		// Loan must be remaped to LoanDetailView
		final LoanDetailView detailView = loanService.mapEntityToDetailViewModel(loan);
		model.addAttribute("detailModel", detailView);
		
		model.addAttribute("minimumExtensionDays", systemPropertyResolver.getIntProperty("min.term.extension.days.Number"));
		model.addAttribute("maximumExtensionDays", systemPropertyResolver.getIntProperty("max.term.extension.days.Number"));

		return "loan_detail";
	}
	
	/**
	 * @return List of possible loan amounts for loan_form select element
	 */
	private List<Integer> setupLoanAmountSelect() {
		final List<Integer> optionValues = new ArrayList<Integer>();

		final int minValue = systemPropertyResolver.getIntProperty("min.amount.Number");
		final int stepValue = systemPropertyResolver.getIntProperty("amount.value.step.Number");
		final int maxValue = systemPropertyResolver.getIntProperty("max.amount.Number");

		int actualValue = minValue;
		int counter = 0;
		for (;;) {
			optionValues.add(actualValue);
			actualValue += stepValue;
			if (actualValue > maxValue || counter == 200) {
				break;
			}
			counter++;
		}
		return optionValues;
	}

}
