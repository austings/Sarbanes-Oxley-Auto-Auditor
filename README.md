# Sarbanes-Oxley-Auto-Auditor
Automatic reporting program for federal compliance

This is a program I wrote for Paychex in 2018. They needed a way to verify that clients under a certain payroll package after onboarding were actually setup correctly with the product they had purchased. It was also a way to identify clients who cancelled or changed payroll within the course of onboarding, and to ensure that all systems were adjusted correctly. This kind of audit is mandated by the Sarbanes Oxley Act of 2002 for most corporations of their size so it was vital to complete each month to ensure federal compliance. 

While I cannot attach the reports or column detail to this repository for privacy reasons, I did include the CSV Reader/and Writer I have created independently. They would save the reports into Report java objects, that the Compile class could then take action on and do the comparisons on. Once the comparisons were complete, it would create an output file with the action that is needed. From there the person running this audit could format it and send it out to different parties to action. 

Before this was implemented, someone would need to spend a whole week doing the comparisons themselves. This also left a lot of room for human error and things were not getting actioned correctly. After I implemented this, people could complete the SOX audit within a day or two, and this also meant that people were getting notified more quickly of when a client's account was wrong. Some measurable consequences of this were:
  -Average onboarding was cut by 15 days for affected clients.
  -5-10% increase in client retention on select clients
  -Avg 40 hours of audit work cut to 12 hours
  
