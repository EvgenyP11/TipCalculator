package com.example.tipcalculator



import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding// binding переменная верхнего уровня в классе для объекта привязки lateinit ключевое слово Обещанее инициализировать переменную.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// привязка View обектов через метод(или конструктор еще не разобрался толком) layoutInflater
        setContentView(binding.root)// корень представления иерархии в приложении

        binding.calculateButton.setOnClickListener{ calculateTip() }//по нажатию на кнопку calculate,по Id происходит привязка к функции и ее вызов.
        binding.costOfServiceEditText.setOnKeyListener { view,keyCod, _ -> handleKeyEvent(view, keyCod) }
    }
/*
функция перещета процентов из введенного числа
 */
    private fun calculateTip(){
        val stringInTextFild = binding.costOfServiceEditText.text.toString()// в переменную привязывается по id из поля EditText берется введенное значение переведенное в строковый формат
        val cost = stringInTextFild.toDoubleOrNull()// переменная из строкового формата переводится в вещественный, может принемать null значения

        if (cost==null){
            binding.tipResult.text= "введите сумму"// проверка н пустое поле EditText
            return}

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId){// впеременную привязывается значение из выбранного чекбокса// в переменную записывается значение процентов которое соответствует переменной полученной из чек бокса
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.13
        }
        var tip = tipPercentage * cost// в переменную вычесляется значение чаевый сумму * на проченты
        val roundUp = binding.roundUpSwitch.isChecked//в переменную привязывается значение переключателя
        if (roundUp) tip = kotlin.math.ceil(tip)//если переключатель включен переменная округляется до целого числа
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)//это какая то привязка к геолокации.
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)// значение в текстовом формате по id привязывается и выводится в поле VIewText
    }

    private fun handleKeyEvent(view: View,keyCod: Int): Boolean{
        if(keyCod== KeyEvent.KEYCODE_ENTER){
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true}
        return false
    }
}