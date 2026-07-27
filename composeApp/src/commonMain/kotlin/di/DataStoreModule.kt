package di

import datastore.DataStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataStoreModule = module {
    single(named("PreferenceDataStore")) { get<DataStoreFactory>().getPreferenceDataStore() }
    single(named("ConfigDataStore")) { get<DataStoreFactory>().getConfigDataStore() }
}
