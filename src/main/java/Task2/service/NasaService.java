package Task2.service;

import Task2.objects.NasaDataObject;

public interface NasaService {

    NasaDataObject data = null;

    NasaService loadData();
    NasaService saveData();

}
