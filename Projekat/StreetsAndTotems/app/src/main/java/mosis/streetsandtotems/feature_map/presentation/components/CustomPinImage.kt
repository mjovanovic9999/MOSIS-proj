package mosis.streetsandtotems.feature_map.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.R
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import mosis.streetsandtotems.feature_map.presentation.util.isPlayerMySquadMember

@Composable
fun CustomPinImage(
    mySquadId: String,
    selectedPlayerSquadId: ProfileData?,
) {
    Box {
        GlideImage(
            imageModel = if (selectedPlayerSquadId?.image_uri == null)
                Uri.EMPTY
            else
                Uri.parse(selectedPlayerSquadId.image_uri),
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .offset(3.dp, 3.dp)
        )
        Image(
            painter = painterResource(
                if (isPlayerMySquadMember(
                        mySquadId,
                        selectedPlayerSquadId?.squad_id
                    )
                )
                    R.drawable.pin_friend
                else
                    R.drawable.pin_other_player
            ),
            contentDescription = null,
            modifier = Modifier.height(48.dp),
        )
    }
}