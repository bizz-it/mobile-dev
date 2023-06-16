package com.example.mobile_dev.ui.agreement

import android.content.Context
import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.data.FranchiseRepository
import com.example.mobile_dev.di.Injection
import java.io.File

class AgreementViewModel(private val franchiseRepository: FranchiseRepository) : ViewModel() {
    private val mTempFile = MutableLiveData<File>()
    val tempFile: LiveData<File> = mTempFile

    fun setFile(file: File) {
        mTempFile.value = file
    }

    private val mData = MutableLiveData<ArrayList<DataAgreement>>()
    val data: LiveData<ArrayList<DataAgreement>> = mData

    fun setData(file: ArrayList<DataAgreement>) {
        mData.value = file
    }

    private val mlatlng = MutableLiveData<List<Address>?>()
    val latlng: LiveData<List<Address>?> = mlatlng

    fun setLatLng(address: List<Address>?) {
        mlatlng.value = address
    }

    fun agreement(dataAgreement: ArrayList<DataAgreement>) =
        dataAgreement[0].photo?.let { photo ->
            dataAgreement[0].doc?.let { doc ->
                franchiseRepository.postAgreement(
                    dataAgreement[0].token,
                    dataAgreement[0].franchise_id,
                    dataAgreement[0].package_id,
                    dataAgreement[0].loc,
                    photo,
                    doc
                )
            }
        }
}

class ViewModelFactory (private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgreementViewModel::class.java)) {
            return AgreementViewModel(Injection.provideRepo(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}