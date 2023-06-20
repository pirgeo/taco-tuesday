import time
import schedule
import requests
from opentelemetry import trace
from opentelemetry.exporter.otlp.proto.http.trace_exporter import (OTLPSpanExporter,)
from opentelemetry.instrumentation.requests import RequestsInstrumentor
from opentelemetry.sdk.resources import Resource
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import (BatchSpanProcessor,)
from opentelemetry.instrumentation.logging import LoggingInstrumentor
import logging
import random
import json
import os

merged = dict()
for name in ["dt_metadata_e617c525669e072eebe3d0f08212e8f2.json", "/var/lib/dynatrace/enrichment/dt_metadata.json"]:
    try:
        data = ''
        with open(name) as f:
          data = json.load(f if name.startswith("/var") else open(f.read()))
        merged.update(data)
    except:
        pass

merged.update({
    "service.name": "taco-proxy"
})

resource = Resource.create(merged)
token_string = "Api-Token " + os.environ['dt_token']
format = "%(asctime)s %(levelname)s [%(name)s] [%(filename)s:%(lineno)d] [trace_id=%(otelTraceID)s span_id=%(otelSpanID)s resource.service.name=%(otelServiceName)s] - %(message)s"
LoggingInstrumentor().instrument(set_logging_format=format)
tracer_provider = TracerProvider(resource=resource)
span_processor = BatchSpanProcessor(OTLPSpanExporter(endpoint=os.environ["dt_endpoint"],headers={ "Authorization": token_string}))
tracer_provider.add_span_processor(span_processor)
RequestsInstrumentor().instrument()
trace.set_tracer_provider(tracer_provider)
tracer = trace.get_tracer(__name__)

def do_constantly():
    with tracer.start_as_current_span("orders"):
        tacos = random.randrange(1, 6)
        resp = requests.get(url="http://orders-internal:8080/orderTacos/" + str(tacos))
        logging.info(resp.status_code)

schedule.every(7).seconds.do(do_constantly)

while True:
    schedule.run_pending()
    time.sleep(.5)

