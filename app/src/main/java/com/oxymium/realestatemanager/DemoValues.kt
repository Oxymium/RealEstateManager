package com.oxymium.realestatemanager

import com.google.android.gms.maps.model.LatLng


// -----------
// DEMO SET 1
// -----------

const val zipCodeDemo1 = "76600"
const val locationDemo1 = "Le Havre"

val addressesDemo1 = listOf(
    "5 allée du Clos Fleuri",
    "59 rue de Belfort",
    "29 rue Galilée",
    "8 rue Hoizey",
    "117 avenue Rouget de Lisle",
    "76 rue Pierre Farcis",
    "29 rue Cauchoise",
    "115 rue Commandant Cousteau",
    "138 boulevard de Strasbourg",
    "140 rue Trois Cent Vingt Neuvieme"
)

val latLngDemo1 = listOf(
    LatLng(49.512805716963214, 0.1044884853367363),
    LatLng(49.511384433726036, 0.11594092199070949),
    LatLng(49.50890580632419, 0.1314532693583722),
    LatLng(49.50970356463477, 0.14066907315829602),
    LatLng(49.50846553203002, 0.14563621450939127),
    LatLng(49.51584921039555, 0.10244312958993604),
    LatLng(49.51920717990698, 0.08725724746571067),
    LatLng(49.52403475783923, 0.09071799477674863),
    LatLng(49.49268294434247, 0.11454545204320525),
    LatLng(49.50247190649094, 0.1203798920180229))

val demoSet1 = List(10) { generateRandomEstate() }

val demoSet1WithAddressAndLatLng = demoSet1
    .zip(addressesDemo1)
    .mapIndexed { index, (estate, address) ->
        val latLng = latLngDemo1.getOrElse(index) { LatLng(0.0, 0.0) }
        estate.copy(
            street = address,
            zipCode = zipCodeDemo1,
            location = locationDemo1,
            latitude = latLng.latitude,
            longitude = latLng.longitude
        )
    }


// -----------
// DEMO SET 2
// -----------
const val zipCodeDemo2 = "76000"
const val locationDemo2 = "Rouen"

val addressesDemo2 = listOf(
    "100 avenue du Mont Riboudet",
    "41 rue Orbe",
    "25 rue Dieutre",
    "8 rue Jules Marie",
    "41 rue du Champ des Oiseaux",
    "24 rampe Cauchoise",
    "27 rue Anatole France",
    "4 place de la Madeleine",
    "96 cavée Saint-Gervais",
    "9 rue Sœur Marie-Ernestine"
)

val latLngDemo2 = listOf(
    LatLng(49.445449488048745, 1.0746636007578743),
    LatLng(49.44338410957576, 1.1036659487172422),
    LatLng(49.446331801415134, 1.110183968068665),
    LatLng(49.44880809606703, 1.1138443790752623),
    LatLng(49.45036909234751, 1.0956386345882407),
    LatLng(49.44649847515371, 1.0863934802350796),
    LatLng(49.44151589436863, 1.084059466141204),
    LatLng(49.44504055805872, 1.0800307243170408),
    LatLng(49.45339103318486, 1.0854003205536362),
    LatLng(49.44232514302433, 1.1240222676494624))


val demoSet2 = List(10) { generateRandomEstate() }

val demoSet2WithAddressAndLatLng = demoSet2
    .zip(addressesDemo2)
    .mapIndexed { index, (estate, address) ->
        val latLng = latLngDemo2.getOrElse(index) { LatLng(0.0, 0.0) }
        estate.copy(
            street = address,
            zipCode = zipCodeDemo2,
            location = locationDemo2,
            latitude = latLng.latitude,
            longitude = latLng.longitude
        )
    }

// -----------
// DEMO SET 3
// -----------

const val zipCodeDemo3 = "74000"
const val locationDemo3 = "Annecy"

val addressesDemo3 = listOf(
    "2 avenue des Marquisats",
    "4 quai Madame de Warens",
    "27 avenue d'Albigny",
    "1 rue Albert Lyard",
    "44 avenue des Îles",
    "44 route de Vovray",
    "86 rue des Marquisats",
    "7 avenue de Tresum",
    "26 avenue de France",
    "1 rue Louis Boch"
)

val latLngDemo3 = listOf(
    LatLng(45.898044, 6.129021),
    LatLng(45.899798436231414, 6.124662740161815),
    LatLng(45.904899, 6.141204),
    LatLng(45.916435, 6.128962),
    LatLng(45.91061626056873, 6.115553875503686),
    LatLng(45.8839869078581, 6.120522781920955),
    LatLng(45.889979151303166, 6.142774621153615),
    LatLng(45.89603165908955, 6.131082988666615),
    LatLng(45.90960880190108, 6.138808284056614),
    LatLng(45.90752273323763, 6.130841404909132))


val demoSet3 = List(10) { generateRandomEstate() }

val demoSet3WithAddressAndLatLng = demoSet3
    .zip(addressesDemo3)
    .mapIndexed { index, (estate, address) ->
        val latLng = latLngDemo3.getOrElse(index) { LatLng(0.0, 0.0) }
        estate.copy(
            street = address,
            zipCode = zipCodeDemo3,
            location = locationDemo3,
            latitude = latLng.latitude,
            longitude = latLng.longitude
        )
    }

