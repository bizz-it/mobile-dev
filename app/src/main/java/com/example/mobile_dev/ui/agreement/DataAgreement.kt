package com.example.mobile_dev.ui.agreement

import java.io.File

data class DataAgreement(var token: String,
                         var name: String,
                         var email: String,
                         var telf: String,
                         var place: String,
                         var date: String,
                         var loc: String,
                         var package_id: String,
                         var franchise_id: String,
                         var photo: File?,
                         var doc: File?)