package com.upaep.colegios.viewmodel.features.payments

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.R
import com.upaep.colegios.model.entities.payments.InBankPayment
import com.upaep.colegios.model.entities.payments.PaymentBankInstructions
import com.upaep.colegios.model.entities.payments.PaymentMethod
import com.upaep.colegios.model.entities.payments.PaymentOptionImageDescription
import com.upaep.colegios.model.entities.payments.PaymentOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor() : ViewModel() {

    private val _selectedMethod = MutableLiveData<PaymentMethod>()
    val selectedMethod: LiveData<PaymentMethod> = _selectedMethod

    fun getPaymentMethods(): List<PaymentMethod> {
        return listOf(
            PaymentMethod(
                id = 1,
                name = "Pago en línea",
                description = "Tarjeta de débito o crédito",
                options = listOf(
                    PaymentOptions(
                        name = "VISA / MASTERCARD",
                        image = listOf(
                            PaymentOptionImageDescription(image = R.drawable.icono_visa, imageSize = 35.dp),
                            PaymentOptionImageDescription(image = R.drawable.icono_mastercard, imageSize = 35.dp)
                        )
                    ),
                    PaymentOptions(
                        name = "AMERICAN EXPRESS",
                        image = listOf(
                            PaymentOptionImageDescription(image = R.drawable.icono_american, imageSize = 35.dp),
                        )
                    )
                )
            ),
            PaymentMethod(
                id = 2,
                name = "Transferencia interbancaria", options = listOf(
                    PaymentOptions(image = listOf(
                        PaymentOptionImageDescription(image = R.drawable.icono_bbva, imageSize = 70.dp),
                    )),
                    PaymentOptions(image = listOf(
                        PaymentOptionImageDescription(image = R.drawable.icono_santander, imageSize = 110.dp),
                    ))
                )
            ),
            PaymentMethod(
                id = 3,
                name = "En sucursal o cajero automático", options = listOf(
                    PaymentOptions(image = listOf(
                        PaymentOptionImageDescription(image = R.drawable.icono_bbva, imageSize = 70.dp),
                    )),
                    PaymentOptions(image = listOf(
                        PaymentOptionImageDescription(image = R.drawable.icono_santander, imageSize = 110.dp),
                    ))
                )
            )
        )
    }

    fun getTableInformation(): List<InBankPayment> {
        return listOf(
            InBankPayment(name = "Banco:", description = "Santander"),
            InBankPayment(name = "Convenio:", description = "5446"),
            InBankPayment(name = "Sucursal:", description = "7980"),
            InBankPayment(name = "Cuenta:", description = "6550465-3513"),
            InBankPayment(name = "CLABE:", description = "01465065504635139"),
            InBankPayment(
                name = "Referencia:",
                description = "[Matricula][Dígito Verificador][Concepto]"
            ),
        )
    }

    fun getInstructionsStepsInBankPayment(): List<PaymentBankInstructions> {
        return listOf(
            PaymentBankInstructions(
                step = "4. ",
                description = "Captura en concepto o descripción la referencia y confirma que sean 11 dígitos, no tenga letras ni símbolos."
            ),
            PaymentBankInstructions(
                step = "5. ",
                description = "En caso de que el banco solicite un referencia numérica captura el siguiente código:",
                extraContent = "9999"
            ),
            PaymentBankInstructions(
                step = "6. ",
                description = "Captura el importe:",
                extraContent = "\$2,100.00"
            ),
            PaymentBankInstructions(
                step = "7. ",
                description = "Verifica el convenio, cuenta, o CLABE, el concepto o descripción y el monto entes correctos."
            ),
            PaymentBankInstructions(
                step = "8. ",
                description = "Confirma la transacción e imprime tu comprobante. Recuerda que no incluye comisión."
            ),
            PaymentBankInstructions(description = "Si la transferencia fue exitosa, tu pago se verá reflejado después de 4 hrs en el estado de cuenta."),
        )
    }

    fun getInstructionsSteps(): List<PaymentBankInstructions> {
        return listOf(
            PaymentBankInstructions(step = "1. ", description = "Ingresa a la banca electrónica"),
            PaymentBankInstructions(
                step = "2. ",
                description = "Dentro del menú puede elegir la opción \"Transferencias\"."
            ),
            PaymentBankInstructions(
                step = "3. ",
                description = "Da de alta una cuenta con la CLABE:",
                extraContent = "014650655046535139"
            ),
            PaymentBankInstructions(
                step = "4. ",
                description = "Captura el siguiente concepto o descripción y confirma que sean 11 dígitos, no tenga letras ni símbolos. De lo contrario la transacción será rechazada y en aproximadamente tu dinero será devuelto en 30 minutos. ",
                extraContent = "[Matricula][DigitoVerificador][Concepto]"
            ),
            PaymentBankInstructions(
                step = "5. ",
                description = "En caso de que el banco solicite un referencia numérica captura el siguiente código:",
                extraContent = "9999"
            ),
            PaymentBankInstructions(
                step = "6. ",
                description = "Captura el importe:",
                extraContent = "\$2,100.00"
            ),
            PaymentBankInstructions(
                step = "7. ",
                description = "Verifica la cuenta CLABE, el concepto o descripción y el monto entes correctos."
            ),
            PaymentBankInstructions(
                step = "8. ",
                description = "Confirma la transferencia y descarga o imprime tu comprobante. Recuerda que no incluye comisión."
            ),
            PaymentBankInstructions(
                step = "9. ",
                description = "Si la transferencia fue exitosa, tu pago se verá reflejado después de 4 hrs en el estado de cuenta."
            ),
        )
    }

    fun updateSelectedMethod(method: PaymentMethod) {
        _selectedMethod.value = method
    }
}