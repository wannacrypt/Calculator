package co.wannacrypt.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private double operand1;
    private double operand2;
    private boolean isNextOperand;
    private boolean isDot;
    private char op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operand1 = 0.0;
        operand2 = 0.0;
        isNextOperand = false;
        isDot = false;
    }

    public void btnRespond(View view) {
        final TextView textView = findViewById(R.id.textView);
        final Button button = findViewById(view.getId());

        switch (button.getText().toString()) {
            case "1":
                updateTextView(textView, '1');
                break;
            case "2":
                updateTextView(textView, '2');
                break;
            case "3":
                updateTextView(textView, '3');
                break;
            case "4":
                updateTextView(textView, '4');
                break;
            case "5":
                updateTextView(textView, '5');
                break;
            case "6":
                updateTextView(textView, '6');
                break;
            case "7":
                updateTextView(textView, '7');
                break;
            case "8":
                updateTextView(textView, '8');
                break;
            case "9":
                updateTextView(textView, '9');
                break;
            case "0":
                updateTextView(textView, '0');
                break;
            case ".":
                updateTextView(textView, '.');
                break;
            case "+/-":
                updateTextView(textView, '-');
                break;
            case "+":
                reset(textView);
                op = '+';
                break;
            case "-":
                reset(textView);
                op = '-';
                break;
            case "✕":
                reset(textView);
                op = '*';
                break;
            case "/":
                reset(textView);
                op = '/';
                break;
            case "√":
                String str = textView.getText().toString();
                str = str.replace(",", "");
                double sqrt = Math.sqrt(Double.valueOf(str));
                textView.setText(new DecimalFormat("#,###.##").format(sqrt));
                isDot = false;
                isNextOperand = false;
                break;
            case "POW":
                reset(textView);
                op = 'p';
                break;
            case "=":
                calculate(textView);
                break;
            case "AC":
                operand1 = 0;
                operand2 = 0;
                textView.setTextSize(95);
                textView.setText("0");
                isDot = false;
                isNextOperand = false;
                break;
        }

    }

    private void reset(TextView textView) {
        isDot = false;
        isNextOperand = true;
        textView.setTextSize(95);
        textView.setText("0");
    }

    private void formatView(String str, TextView textView) {
        if (str.length() < 8) {
            textView.setTextSize(95);
            textView.setText(str);
        }
        else if (str.length() < 10) {
            textView.setTextSize(81);
            textView.setText(str);
        } else if (str.length() < 11) {
            textView.setTextSize(71);
            textView.setText(str);
        } else if (str.length() < 12) {
            textView.setTextSize(64);
            textView.setText(str);
        }
    }

    private void updateTextView(TextView textView, char ch) {
        if (!isNextOperand) {
            operand1 = updateOperand(textView, ch, operand1);
        } else {
            operand2 = updateOperand(textView, ch, operand2);
        }
    }

    private double updateOperand(TextView textView, char  ch, double operand) {
        String str = textView.getText().toString();
        str = str.replace(",", "");
        operand = Double.valueOf(str);
        if (ch == '.') {
            isDot = true;
            textView.setText(str + '.');
            return operand;
        }

        if (ch == '-') {
            operand *= -1;
            textView.setText(new DecimalFormat("#,###.##").format(operand));
            return operand;
        }

        if (!isDot) {
            double carry = operand - (int)operand;
            operand = (int)operand * 10 + (ch - 48);
            operand += carry;
            str = new DecimalFormat("#,###.##").format(operand);
        } else
            str += ch;

        formatView(str, textView);

        return operand;
    }

    private void calculate(TextView textView) {
        String str;
        switch (op) {
            case '+':
                operand1 += operand2;
                str = new DecimalFormat("#,###.##").format(operand1);
                formatView(str, textView);
                break;
            case '-':
                operand1 -= operand2;
                str = new DecimalFormat("#,###.##").format(operand1);
                formatView(str, textView);
                break;
            case '*':
                operand1 *= operand2;
                str = new DecimalFormat("#,###.##").format(operand1);
                formatView(str, textView);
                break;
            case '/':
                operand1 /= operand2;
                str = new DecimalFormat("#,###.##").format(operand1);
                formatView(str, textView);
                break;
            case 'p':
                operand1 = Math.pow(operand1, operand2);
                str = new DecimalFormat("#,###.##").format(operand1);
                formatView(str, textView);
                break;
        }
    }

}
