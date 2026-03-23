using UnityEngine;
using UnityEngine.SceneManagement;

public class level_button_scripts : MonoBehaviour
{
   public void restart()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

    public void returnToMain()
    {
        SceneManager.LoadScene("Main menu");
    }


}
