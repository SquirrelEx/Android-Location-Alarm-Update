package moondimemap.destinationalarm

import android.Manifest
import android.R
import android.app.AlertDialog
import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider


class LocationService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        val isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val web3 = Web3j.build(HttpService("https://rinkeby.infura.io/v3/b5bf9e9d22514ecfbe25f474f387774f"));
        val credentials = Credentials.create("02381aa245d8a4772385db9abeb10909d661757f73fdb7f0cdb6ccd17396e920"); // private key of dev account

        //val transactionManager = ClientTransactionManager(web3, credentials.address, 3, 5000);

        val contract = MoondimeToken.load(
            "0x3dd8404CcFB923B3a65EFb5E0475ea853C2Db26C",
            web3,
            credentials,
            DefaultGasProvider()
        )

        val blockchainBridge = BlockchainBridge(web3, credentials, contract);

        val disposable = rxPermissions!!.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe { granted ->
                    if (granted) {
                        if (isGPSEnabled) {
                            if (locationManager != null) {

                                val gpsLocationListener = object : LocationListener {
                                    override fun onLocationChanged(loc: Location) {
                                        if (check) {
                                            val results = FloatArray(3)
                                            Location.distanceBetween(loc.latitude, loc.longitude, targetMarker!!.position.latitude, targetMarker!!.position.longitude, results)
                                            if (results[0] <= minDist) {

                                                blockchainBridge.mintToken();

                                                // do stuff
                                                ringtone!!.play()
                                                superDirty!!.vibrateIt()
                                                map!!.overlays.remove(circle)
                                                map!!.overlays.remove(targetMarker)
                                                check = false
                                                map!!.invalidate()
                                                val builder = AlertDialog.Builder(superDirty)
                                                builder.setTitle("Successfully invested HKD5")
                                                        .setMessage("Your total moondimes: " + blockchainBridge.getWalletBalance().toString() + " MDT")
                                                        .setPositiveButton(R.string.yes) { dialog, _ ->
                                                            dialog.cancel()
                                                        }.setIcon(R.drawable.ic_dialog_alert)
                                                        .setOnCancelListener {
                                                            ringtone!!.stop()
                                                            vib!!.cancel()
                                                            vibrating = false
                                                        }
                                                        .show()
                                            }
                                        }
                                    }

                                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                                    override fun onProviderEnabled(provider: String) {}
                                    override fun onProviderDisabled(provider: String) {
                                        superDirty!!.showGPSDialog()
                                    }
                                }
                                try {
                                    locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                            1, 1f,
                                            gpsLocationListener)
                                    startForeground(16, notif)
                                }
                                catch (e: SecurityException) {
                                    // TODO handle this!
                                }
                            }
                        }
                        else {
                            superDirty!!.showGPSDialog()

                        }
                    }
                    else {
                        val builder = AlertDialog.Builder(superDirty)
                        builder.setTitle("PERMISSIONS ERROR!")
                                .setMessage("The app cannot work without the required permissions. Exiting...")
                                .setPositiveButton(R.string.ok) { _, _ ->
                                    superDirty!!.finish()
                                }.setIcon(R.drawable.ic_dialog_alert)
                                .setOnCancelListener {
                                    superDirty!!.finish()
                                }
                                .show()
                    }
                }
        return null
    }
}