using UnityEngine;
using UnityEngine.SceneManagement;

public class Esc_button_TestScene : MonoBehaviour
{
    public void GetOut_Button()
    {
        SceneManager.LoadScene("Main Menu");
    }
}
