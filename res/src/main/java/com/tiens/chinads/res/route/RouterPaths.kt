package com.tiens.chinads.res.route

interface RouterPaths {

    interface Owner{
        companion object {
            const val OWNER_DATA_PROVIDER = "/Owner/OwnerDataProvider"
            const val OWNER_ACTIVITY = "/Owner/OwnerActivity"
        }
    }

    interface Main{
        companion object {
            const val MAIN_ACTIVITY = "/Main/MainActivity"
            const val TEST_REFRESH_ACTIVITY = "/Main/TestRefreshActivity"
            const val APP_DATA_PROVIDER = "/Main/AppDataProvider"
        }
    }
}