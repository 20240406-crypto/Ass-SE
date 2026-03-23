using UnityEngine;
using UnityEngine.SceneManagement;

public class Button_scripts : MonoBehaviour
{
    
    public void playTest()
    {
        // this is a system for deticting picked choices inside of the main menu STILL unimplemented tho  
        //int selectedScene = int.Parse(UnityEngine.EventSystems.EventSystem.current.currentSelectedGameObject.name);
        //GameManger.Instance._select = selectedScene;
        SceneManager.LoadScene("SampleScene");
    }


    // no naming conventions for the levels as they are unique functions here (IMO)
    public void FirstLevel()
    {
        SceneManager.LoadScene("FirstLevel");
    }

    public void SecondLevel()
    {
        SceneManager.LoadScene("SecondLevel");
    }


    public void  exit()
    {
        Application.Quit();
    }
}
