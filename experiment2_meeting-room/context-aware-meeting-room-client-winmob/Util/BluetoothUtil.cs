using System;
using System.Collections.Generic;
using System.Text;
using InTheHand.Net;
using InTheHand.Net.Bluetooth;
using InTheHand.Net.Sockets;
using System.Net;

namespace CAMR_3
{
    class BluetoothUtil
    {
        //Vil ikke slå ut på unittest mot emulator pga feil/mangler i bluetooth emulator konfig
        public static string FindMacAddressInTheHand() {
            BluetoothRadio radio = BluetoothRadio.PrimaryRadio;
            String adress = radio.LocalAddress.ToString();

            return adress;
        }
    }
}
