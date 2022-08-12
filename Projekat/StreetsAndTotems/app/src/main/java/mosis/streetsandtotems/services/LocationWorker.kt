//package mosis.streetsandtotems.services
//
//import android.content.Context
//import android.widget.Toast
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import androidx.work.workDataOf
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.location.Priority
//import mosis.streetsandtotems.core.WorkerKeys
//import mosis.streetsandtotems.feature_map.domain.LocationDTO
//
//
//class LocationWorker(
//    private val context: Context,
//    workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams) {
//
//
//    override suspend fun doWork(): Result {
//        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//        val locationResult =
//            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, null)
//
//
//        return locationResult.let {
//            if (it.isSuccessful) {
//                val lastKnownLocation = it.result
//
//                if (lastKnownLocation != null) {
///*                    Toast.makeText(this, lastKnownLocation.latitude.toString(), Toast.LENGTH_SHORT)
//                        .show()
//                    Toast.makeText(this, lastKnownLocation.longitude.toString(), Toast.LENGTH_SHORT)
//                        .show()*/
////                    myLocation.value =
//                    val newLocation = LocationDTO(
//                        lastKnownLocation.latitude,
//                        lastKnownLocation.longitude,
//                        lastKnownLocation.accuracy
//                    )
//                    Toast.makeText(
//                        context,
//                        lastKnownLocation.accuracy.toString() + "low",
//                        Toast.LENGTH_SHORT
//                    )
//                    Result.success(
//                        workDataOf(
//                            WorkerKeys.NEW_LOCATION to newLocation
//                        )
//                    )
//                } else {
//                    Toast.makeText(context, "NULL", Toast.LENGTH_SHORT).show()
//                    Result.failure()
//                }
//            } else {
//                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
//                Result.failure()
//            }
//        }
//
//    }
//}