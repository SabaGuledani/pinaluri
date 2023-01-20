package com.saba.spark

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.saba.spark.databinding.FragmentProfileSettingBinding
import java.io.ByteArrayOutputStream


class ProfileSettingFragment : Fragment(R.layout.fragment_profile_setting) {

    private lateinit var binding: FragmentProfileSettingBinding
    private lateinit var email: String
    private lateinit var password: String


    private var imageSet = false


    private var getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(binding.circleimg)

            }
            imageSet = true
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileSettingBinding.bind(view)

        val database = Firebase.database("https://spark-1e90d-default-rtdb.firebaseio.com/")
        val myRef = database.reference
        var storageRef = Firebase.storage.reference
        var auth = FirebaseAuth.getInstance().currentUser?.uid

        email = ProfileSettingFragmentArgs.fromBundle(requireArguments()).email
        password = ProfileSettingFragmentArgs.fromBundle(requireArguments()).password

        binding.circleimg.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )

            } else {
                getImageFromGallery.launch("image/*")
            }
        }


        binding.registerBtn.setOnClickListener {

            if (binding.circleimg.drawable != null && imageSet) {
                val imageRef = storageRef.child("images/${auth}.jpg")
                val bitmap = getBitmapFromView(binding.circleimg)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        activity,
                        "An Error Has Occurred, Try a Different Image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                uploadTask.addOnSuccessListener {
                    var name = binding.name.editText?.text.toString()
                    if(name!= "".trim()) {

                        var profileImg = "images/${auth}.jpg"
                        var profileObject = userProfile(auth.toString(), name, profileImg)
                        myRef.child("profile").child(auth.toString())
                            .setValue(profileObject)
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }else{
                        Toast.makeText(requireContext(), "name is empty!", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            } else {
                Toast.makeText(activity, "Select An Image", Toast.LENGTH_SHORT).show()
            }

        }


    }


}

private fun getBitmapFromView(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(
        view.width, view.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}






