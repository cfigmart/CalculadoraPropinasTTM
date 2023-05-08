package edu.unicauca.aplimovil.calculadorapropinas

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
    var propinaIngresada by remember { mutableStateOf("") }
    var valorIngresado by remember { mutableStateOf("12") }
    var cantidad = valorIngresado.toDoubleOrNull() ?: 0.0

    val porcentajePropina = propinaIngresada.toDoubleOrNull() ?: 0.0
    val propina = calcularPropina(cantidad, porcentajePropina)

    val focusManager = LocalFocusManager.current

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
        //Este para agregar el valor de lo que voy a pagar
        CampoTextoValorServicio(
            label = R.string.costo_de_servicio,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down)}
            ),
            valor = valorIngresado,
            siCambiaValor = { valorIngresado = it })
        //Donde ingreso el porcentaje de la propina
        CampoTextoValorServicio(
            label = R.string.propina,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            valor = propinaIngresada,
            siCambiaValor = { propinaIngresada = it}
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.valor_propina, propina),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}


@Composable
fun CampoTextoValorServicio(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    valor: String,
    siCambiaValor: (String)-> Unit,
    modifier: Modifier = Modifier,
) {


    TextField(
        //label = { Text(stringResource(R.string.costo_de_servicio))},
        label = { Text(stringResource(label)) },
        value = valor,
        onValueChange = siCambiaValor,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)


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