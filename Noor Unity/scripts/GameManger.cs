using UnityEngine;

public class GameManger : MonoBehaviour
{
    public static GameManger Instance;

    [SerializeField]
    string selected_Scene;

    private int select;
    public int _select
    {  get { return select; } 
        set { select = value; } 
    }



    // this is suppose to keep only 1 instance of this code in someplace 
    // as i dont need it for the moment iam not using it but it seems extermely useful and i thought i might use it in some way to make the levels
    /*
    private void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
        }
    }
    */



}//class














