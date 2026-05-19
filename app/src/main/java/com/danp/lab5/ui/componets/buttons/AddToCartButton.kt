package com.danp.lab5.ui.componets.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Botón para agregar un producto al carrito.
 *
 * @param onAddToCart   Acción al pulsar el botón.
 * @param isInCart      Si el producto ya está en el carrito cambia a modo "outline".
 * @param enabled       Desactiva el botón si el producto no tiene stock.
 * @param modifier      Modifier externo.
 */
@Composable
fun AddToCartButton(
    onAddToCart: () -> Unit,
    isInCart: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val label = when {
        !enabled   -> "Sin stock"
        isInCart   -> "Agregado al carrito"
        else       -> "Agregar al carrito"
    }

    if (isInCart) {
        OutlinedButton(
            onClick = onAddToCart,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )
            Text(
                text = label,
                modifier = Modifier.weight(1f, fill = false),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        Button(
            onClick = onAddToCart,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )
            Text(
                text = label,
                modifier = Modifier.weight(1f, fill = false),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}