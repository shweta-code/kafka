*******************************************************************
##					ica-inbound-processor ####
********************************************************************

The module services the following purposes:
1. De-serialize based on schema for the topic
2. Filter messages based on configured rules
   2.1 Allow only .com events (seller_id is zero)
3. Create canonical object that is used in rest of the application
4. Enrich canonical object by  adding derived fields
   4.1 Should be easy to create new fields non intrusively
5. Convert to json and send it on kafka topic "ItemEventLogChannel"

###########################################################################
