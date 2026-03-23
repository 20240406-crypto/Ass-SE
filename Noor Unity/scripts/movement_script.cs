using UnityEngine;
using UnityEngine.InputSystem;
using UnityEngine.UI;

public class movement_script : MonoBehaviour
{
    public float speed = 5 ;
    public Transform Transform;
    

    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        Vector3 pos;
        pos= transform.position;


        transform.position = pos;


        if (Input.GetKey(KeyCode.W))
        {
            pos.x +=speed *Time.deltaTime;
        }

        if (Input.GetKey(KeyCode.S))
        {
            pos.x -= speed * Time.deltaTime;
        }

        if (Input.GetKey(KeyCode.D))
        {
            pos.z -= speed * Time.deltaTime;
        }

        if (Input.GetKey(KeyCode.A))
        {
            pos.z += speed * Time.deltaTime;
        }

        transform.position = pos;


    }
}
