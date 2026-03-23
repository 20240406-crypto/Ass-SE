using System.Collections;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.SceneManagement;

public class Enemy1_movement : MonoBehaviour
{
    [SerializeField]
    private float speed;

    [SerializeField]
    private float crawl_delay;

    [SerializeField]
    private Rigidbody rb;


    public Transform target;

    public float look_Radius= 7f;
    public float attack_Radius = 2;

    
    public NavMeshAgent agent;



    private int random_Move;
    private int random_Direcation;




    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        
        agent = GetComponent<NavMeshAgent>();
    }

    // Update is called once per frame
    void Update()
    {
        if (!target)
        {
            //auto replay after death 
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
            return;
        }

        //lots a code for AI info down 
        float distance = Vector3.Distance(transform.position, target.position);
        if (distance<=look_Radius)
        {
            
            agent.SetDestination(target.position);
        }
        if (distance <= attack_Radius) { 
        Destroy(target.gameObject);
        }
        // code for random patrol 
        if (agent.stoppingDistance >= agent.remainingDistance)
        {
            Vector3 point; 
            random_Move = Random.Range(-10,10);
            if (set_Random_Point(transform.position , random_Move,out point))
            {
                agent.SetDestination(point);
            }
        }
        

        

    }


    bool set_Random_Point(Vector3 centre , float range , out Vector3 Result) 
    {
        Vector3 destenation = centre + Random.insideUnitSphere *range ;
        NavMeshHit hit; 

        if (NavMesh.SamplePosition(destenation , out hit , 1.0f , NavMesh.AllAreas))
        {
            Result = hit.position;
            return true; 
        }
        Result = Vector3.zero;
        return false;
    }










    private void OnDrawGizmosSelected() // draws the radius in inspector 
    {
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(transform.position, look_Radius);

    }
    

    //original movement 
    IEnumerator Crawl()
    {
        
            yield return new WaitForSeconds(crawl_delay);
            rb.AddForce(0,0,speed,ForceMode.Impulse);
        
    }
    
    void random_Roaming()//trying to implement the random movement when idle
    {
        if (!agent.isStopped)
        {
            random_Move = Random.Range(-1, 10);
            random_Direcation = Random.Range(0, 2);
            if (random_Direcation == 0)
            {
                agent.SetDestination(Vector3.forward * random_Move);

            }
            else
            {
                agent.SetDestination(Vector3.left * random_Move);
            }

        }
    }
}
