using UnityEngine;

public class SecondLevel_CameraMovement : MonoBehaviour
{
    [SerializeField]
    private Transform target ;
    private Vector3 offset ;
    void Start()
    {
        offset = transform.position;
    }

    // Update is called once per frame
    void Update()
    {
        if (!target)
        {
            return;
        }
        camera_Position();
    }

    void camera_Position()
    {
        if(target.position.z >= 14 || target.position.z <= -9)
        {
            return;
        }
        offset.z = Mathf.Lerp(target.position.z, transform.position.z, 0.1f);
        transform.position = offset;
    }
}
