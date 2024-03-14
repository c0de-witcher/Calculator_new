package com.example.calculator_new

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.calculator_new.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
private lateinit var  binding : ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var firstnumber = ""
        var currentnumber = ""
        var currentoprater = ""
        var result = ""

        binding.grid.children.filterIsInstance<Button>().forEach { button ->
            button.setOnClickListener {
                val buttontext = button.text.toString()
                when{
                    buttontext.matches(Regex("[0-9]"))->{
                        if(currentoprater.isEmpty()){
                            firstnumber+=buttontext
                            binding.tvresult.text=firstnumber
                        }
                        else{
                            currentnumber+=buttontext
                            binding.tvresult.text="$firstnumber$currentoprater$currentnumber"
                        }
                    }
                    buttontext.matches(Regex("[+/*-]"))->{
                        currentnumber=""
                        if(binding.tvresult.text.toString().isEmpty()){
                            currentoprater=buttontext
                            binding.tvresult.text = "0"
                        }
                        else{
                            currentoprater=buttontext
                            binding.tvresult.text="$firstnumber$currentoprater"
                        }
                    }
                    buttontext=="="->{
                        if (currentnumber.isNotEmpty() && currentoprater.isNotEmpty()){
                            binding.tvFormula.text = "$firstnumber$currentoprater$currentnumber"
                            result = evaluateExpression(firstnumber,currentnumber,currentoprater)
                            firstnumber = result
                            currentnumber = ""
                            binding.tvresult.text =result
                        }

                    }
                    buttontext=="."->{
                        if(currentoprater.isEmpty()){
                            if(!firstnumber.contains(".")){
                                if(firstnumber.isEmpty()) firstnumber+="0$buttontext"
                                else firstnumber+=buttontext
                                binding.tvresult.text = firstnumber
                            }
                            else{
                                if (!currentnumber.contains(".")){
                                    if(currentoprater.isEmpty()) currentnumber+="0$buttontext"
                                    else currentnumber+=buttontext
                                    binding.tvresult.text = currentnumber
                                }
                            }
                        }
                    }

                    buttontext=="AC"->{
                        currentnumber=""
                        firstnumber=""
                        currentoprater=""
                        binding.tvresult.text=""
                        binding.tvFormula.text=""
                    }


                }
            }
        }
    }
    fun evaluateExpression(firstnumber:String,secondNumber:String,operstor:String):String{
        val num1 = firstnumber.toDouble()
        val num2 = secondNumber.toDouble()
        return when(operstor){
            "+"->(num1+num2).toString()
            "-"->(num1-num2).toString()
            "*"->(num1*num2).toString()
            "/"->(num1/num2).toString()

            else -> ""
        }
    }

}