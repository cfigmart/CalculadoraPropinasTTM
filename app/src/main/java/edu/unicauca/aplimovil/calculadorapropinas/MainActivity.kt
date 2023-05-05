package edu.unicauca.aplimovil.calculadorapropinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.unicauca.aplimovil.calculadorapropinas.ui.theme.CalculadoraPropinasTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraPropinasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PantallaPrincipalPropinas()
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipalPropinas() {
    var valorIngresado by remember {mutableStateOf("12")}
    var cantidad = valorIngresado.toDoubleOrNull()?: 0.0
    val propina = calcularPropina(cantidad)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.calcular_propina),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        CampoTextoValorServicio(valorIngresado, {valorIngresado = it})
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.valor_propina, ""),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }

}

@Composable
fun CampoTextoValorServicio(
    valor: String,
    onValueChange: (String)-> Unit
) {


    TextField(
        label = { Text(stringResource(R.string.costo_de_servicio))},
        value = valor,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraPropinasTheme {
        PantallaPrincipalPropinas()
    }
}

fun calcularPropina(
    costoServicio: Double,
    porcentajePropina:Double = 15.0,
):String{
    val propina = (porcentajePropina/100) * costoServicio
    return NumberFormat.getCurrencyInstance().format(propina)
}