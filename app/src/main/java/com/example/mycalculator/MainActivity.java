package com.example.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private TextView calculatorTextView;
    private TextView resultTextView;
    private StringBuilder currentInput = new StringBuilder();
    private BigDecimal operand1 = BigDecimal.ZERO;
    private char operator = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorTextView = findViewById(R.id.TextViewCalculator);
        resultTextView = findViewById(R.id.TextView);

        setNumberButtonClickListeners();
        setOperatorButtonClickListeners();
        setEqualsButtonClickListener();
        setClearButtonClickListener();
        setDecimalButtonClickListener();
    }

    private void setNumberButtonClickListeners() {
        int[] numberButtonIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

        for (int buttonId : numberButtonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentInput.append(button.getText().toString());
                    calculatorTextView.setText(currentInput.toString());
                }
            });
        }
    }

    private void setOperatorButtonClickListeners() {
        int[] operatorButtonIds = {R.id.buttonPluss, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide};

        for (int buttonId : operatorButtonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentInput.length() > 0) {
                        operand1 = new BigDecimal(currentInput.toString());
                        operator = button.getText().charAt(0);
                        currentInput.setLength(0);
                    } else if (operator != ' ') {
                        // If currentInput is empty, but there's already an operator, update the operator
                        operator = button.getText().charAt(0);
                    }
                }
            });
        }
    }


    private void setEqualsButtonClickListener() {
        Button equalsButton = findViewById(R.id.buttonEquals);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.length() > 0) {
                    BigDecimal operand2 = new BigDecimal(currentInput.toString());
                    BigDecimal result = performOperation(operand1, operand2, operator);
                    resultTextView.setText(result.toString());
                    currentInput.setLength(0);
                }
            }
        });
    }

    private void setClearButtonClickListener() {
        Button clearButton = findViewById(R.id.buttonClean);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput.setLength(0);
                operand1 = BigDecimal.ZERO;
                operator = ' ';
                calculatorTextView.setText("");
                resultTextView.setText("");
            }
        });
    }

    private void setDecimalButtonClickListener() {
        Button decimalButton = findViewById(R.id.buttonComma);
        decimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.toString().contains(".")) {
                    currentInput.append(".");
                    calculatorTextView.setText(currentInput.toString());
                }
            }
        });
    }

    private BigDecimal performOperation(BigDecimal operand1, BigDecimal operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1.add(operand2);
            case '-':
                return operand1.subtract(operand2);
            case '*':
                return operand1.multiply(operand2);
            case '/':
                if (!operand2.equals(BigDecimal.ZERO)) {
                    return operand1.divide(operand2, 10, BigDecimal.ROUND_HALF_UP);
                } else {
                    // Handle division by zero
                    return BigDecimal.ZERO; // You can handle this case differently
                }
            default:
                return BigDecimal.ZERO; // You can handle this case differently
        }
    }
}
