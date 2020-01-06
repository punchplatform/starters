from datetime import timedelta, datetime


job_info = {
    'owner': 'punchplatform',
    'depends_on_past': False,
    'start_date': datetime.now() - timedelta(days=1),
    'email': ['contact@punchplatform.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
    'punch_channel': 'suspect_ip_detection',
    'punch_tenant': 'mytenant',
    'plan': {
        'from': datetime.now() - timedelta(minutes=1),
        'to': datetime.now(),
    }
}
