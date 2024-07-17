import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Put your name here
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        view.updateBottomDisplay(model.bottom());
        view.updateTopDisplay(model.top());

        // Additional logic for enabling/disabling specific buttons based on conditions
        NaturalNumber bottom = model.bottom();
        NaturalNumber top = model.top();

        if (!bottom.isZero()) {
            view.updateDivideAllowed(true);
        } else {
            view.updateDivideAllowed(false);
        }

        if (bottom.compareTo(top) >= 0) {
            view.updateSubtractAllowed(false);
        } else {
            view.updateSubtractAllowed(true);
        }

        if (bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) <= 0) {
            view.updateRootAllowed(true);
        } else {
            view.updateRootAllowed(false);
        }

        if (bottom.compareTo(INT_LIMIT) <= 0) {
            view.updatePowerAllowed(true);
        } else {
            view.updatePowerAllowed(false);
        }

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        NaturalNumber newTop = this.model.bottom();
        NaturalNumber top = this.model.top();

        top.transferFrom(newTop);
        this.view.updateRootAllowed(false);
        this.view.updateDivideAllowed(false);
        this.view.updateDivideAllowed(false);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        bottom.add(top);
        top.clear();
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        if (bottom.compareTo(top) <= 0) {
            top.subtract(bottom);
            bottom.transferFrom(top);

        }
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        bottom.multiply(top);
        top.clear();
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        NaturalNumber rem = top.divide(bottom);
        bottom.transferFrom(top);
        top.transferFrom(rem);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.power(bottom.toInt());
        bottom.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        NaturalNumber initialRoot = this.model.bottom();
        NaturalNumber rooted = this.model.top();
        rooted.root(initialRoot.toInt());
        initialRoot.transferFrom(rooted);
        rooted.clear();
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        // Add a new digit to the bottom number
        NaturalNumber bottom = this.model.bottom();
        bottom.multiplyBy10(digit);

        // Update view to reflect changes in model
        updateViewToMatchModel(this.model, this.view);
    }

}
