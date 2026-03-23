using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class simple_movement_sadPlayer : MonoBehaviour
{
    public Animator animator;
    public Rigidbody rb;

    public float speed = 5;
    public float rotate_Speed = 30;

    

    float horizantal_Move;
    float vertical_Move;

    public Transform obj_Rotate;

    [SerializeField]
    private Canvas canvas;

    private void Start()
    {
        Time.timeScale = 1f;
    }

    private void Update()
    {
        StartCoroutine(moving());
        horizantal_Move = Input.GetAxis("Horizontal");
        vertical_Move = Input.GetAxis("Vertical");

        
        //transform.Translate(Vector3.right * horizantal_Move * speed * Time.deltaTime);


        // locking the axis to a certin postion based off the view 
        // better for a more stylized videogame i notice but its still pretty nice system

        if (vertical_Move > 0)
        {
            
            transform.rotation = Quaternion.Euler(0, -90, 0);
            transform.Translate(Vector3.forward * vertical_Move * speed * Time.deltaTime);
        }
        if (vertical_Move < 0)
        {
            transform.rotation = Quaternion.Euler(0, 90, 0);
            transform.Translate(Vector3.forward * -(vertical_Move) * speed * Time.deltaTime);
        }

        if (horizantal_Move >0)
        {
            transform.rotation = Quaternion.Euler(0, 0, 0);
            transform.Translate(Vector3.forward * speed * Time.deltaTime);
        }
        if (horizantal_Move < 0)
        {
            transform.rotation = Quaternion.Euler(0, -180, 0);
            transform.Translate(Vector3.forward  * speed * Time.deltaTime);
        }

        // old ways that i tried to fix the rotation code 
        //transform.Rotate(Vector3.up * rotate_Speed * vertical_Move * Time.deltaTime);
        //transform.Rotate(Vector3.up*rotate_Speed *horizantal_Move*Time.deltaTime);

      




    }


    IEnumerator moving() // simple movement animation using a different non-RIGIDBODY method
    {
        Vector3 strt = transform.position;
        yield return new WaitForSeconds(0.02f);
        
        if (strt.x != transform.position.x| strt.z != transform.position.z) { 
            
            animator.SetBool("MOVING", true);
        }
        else
        {
            animator.SetBool("MOVING", false);
        }
    }




    private void OnCollisionEnter(Collision collision)
    { // i notice now that putting the canvas here is pretty dumb where u can just check if time is stopped anywhere and show it there in a different script
      // UNDERSTOOD NOW AT LEAST!
        if (collision.gameObject.CompareTag("Finish"))
        {
            Time.timeScale = 0f; 
            canvas.gameObject.SetActive(true);
        }

    }

}
