package input;

import java.util.ArrayList;

public final class HomeworkJSONTemplate {
    private int numberOfTurns;
    private InitialDataInput initialData;
    private ArrayList<MonthlyUpdatesInput> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialDataInput getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialDataInput initialData) {
        this.initialData = initialData;
    }

    public ArrayList<MonthlyUpdatesInput> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final ArrayList<MonthlyUpdatesInput> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
