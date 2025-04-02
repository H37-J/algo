-- 가장 비용이 많이 드는 쿼리 찾기
SELECT TOP 10 qs.execution_count,
              qs.total_logical_reads / qs.execution_count                AS avg_logical_reads,
              qs.total_elapsed_time / qs.execution_count                 AS avg_elapsed_time,
              SUBSTRING(qt.text, (qs.statement_start_offset / 2) + 1,
                        ((CASE qs.statement_end_offset
                              WHEN -1 THEN DATALENGTH(qt.text)
                              ELSE qs.statement_end_offset
                              END - qs.statement_start_offset) / 2) + 1) AS query_text
FROM sys.dm_exec_query_stats AS qs
         CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt
ORDER BY qs.total_logical_reads DESC;

