package di

import de.thro.packsimulator.viewmodel.*
import org.koin.dsl.module

// Define the Koin module
val appModule = module {
    single { AccountViewModel() } // Provide AccountViewModel as a singleton
    single { PackViewModel() } // Provide PackViewModel as a singleton
    single { SetViewModel() } // Provide SetViewModel as a singleton
}
