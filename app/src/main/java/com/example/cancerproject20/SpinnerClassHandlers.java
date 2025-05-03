package com.example.cancerproject20;

import android.view.View;
import android.widget.AdapterView;


public class SpinnerClassHandlers {

    static String[] classicSpinnerValues = {"1 כלל לא", "2", "3", "4", "5", "6", "7", "8", "9", "10 בלתי נסבל "};
    static String[] spinner1Values = {"כאבי ראש", "לא קיים","מעט","פעימות בראש", "צילצולים", "מגרנות", "לא נסבל", "מושבט"};
    static String[] spinner2Values = {"בחילה","אין","מעט", "סחרחורת"};
    static String[] spinner3Values = {"כאב בטן", "אין", "מעט", "קיבוצים", "בילתי נסבל"};

    static class SpinnerHandle implements AdapterView.OnItemSelectedListener
    {
        private String[] field;
        private String selectedData;

        public SpinnerHandle(String[] field)
        {
            this.field = field;
            this.selectedData = null;
        }

        public String getSelectedData(){
            return  this.selectedData;
        }

        public String getFieldTitle(){
            return this.field[0];
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            this.selectedData = this.field[position]; // save the data to the object


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
