using System;

using System.Collections.Generic;
using System.Text;
using System.IO;

namespace CAMR_3
{
    class FileUtil
    {
        private const String directory = @"My Documents\CAMRdir";
        private const String filename = @"My Documents\CAMRdir\CAMRdata.txt";

        public FileUtil() {}

        public void SaveToFile(String text)
        {
            if (!Directory.Exists(directory))
                Directory.CreateDirectory(directory);

            Directory.SetCurrentDirectory(directory);

            StreamWriter writer = new StreamWriter(new FileStream(filename, FileMode.Append));
            writer.Write(text + "\n");
            writer.Close();

        }

        public String ReadFromFile()
        {
            if (!Directory.Exists(directory))
                return "Directory / file dows not exist";
            else
            {
                return "";
            }
        }

    }
}
