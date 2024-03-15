package com.example.kms.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.example.kms.databinding.FragmentQRScanBinding
import com.example.kms.viewmodels.QRScanViewModel

class QRScanFragment : Fragment() {

     lateinit var codeScanner: CodeScanner

    private val CAMERA_PERMISSION_REQUEST_CODE = 10
    private val viewModel: QRScanViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentQRScanBinding.inflate(layoutInflater, container, false)
        val  scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        checkCameraPermission(binding)
        return binding.root
    }


    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()

    }

    private fun checkCameraPermission(binding: FragmentQRScanBinding) {
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 10 )
            startScanning(binding)
        } else {
            startScanning(binding)
        }
    }


    private fun startScanning(binding: FragmentQRScanBinding) {
        val  scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            decodeCallback = DecodeCallback {
                releaseResources()
                activity.runOnUiThread {
                    Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()

                }
            }
            errorCallback = ErrorCallback {
                activity.runOnUiThread {
                    Toast.makeText(activity, it.message + "aaaaaaaaa", Toast.LENGTH_LONG).show()
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                isPermissionGranted = true
//                codeScanner.startPreview()
//            } else {
//                isPermissionGranted = false
//            }
//        }
//    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

}