function version()
	return "1.0.0"
end

function exec(sql, ...)
	local stmt = db.prepare(sql)
	count = stmt:exec(...)
	return count
end

function query_begin(sql, ...)
    local stmt = db.prepare(sql)
    local rs = stmt:query(...)
    local r = {}
    local colcnt = rs:colcnt()
    local colmetas = stmt:column_info()
    while rs:next() do
        local k = {rs:get()}
        for i = 1, colcnt do
            if k[i] == nil then
                k[i] = {}
            end
        end
        table.insert(r, k)
    end
    if (#r == 0) then
        return {colcnt=colcnt, rowcnt=0 , rows=nil, colmetas=colmetas}
    end

    return {colcnt=colcnt, rowcnt=#r, rows=r, colmetas=colmetas, snapshot=db.getsnap()}
end

function query_next(snap, sql, ...)
	db.open_with_snapshot(snap)
	local rs = db.query(sql, ...)
	local r = {}
	local colcnt = rs:colcnt()
	while rs:next() do
		local k = {rs:get()}
		for i = 1, colcnt do
			if k[i] == nil then
				k[i] = {}
			end
		end
		table.insert(r, k)
	end
	if (#r == 0) then
		return {colcnt=colcnt, rowcnt=0 , rows=nil}
	end

	return {colcnt=colcnt, rowcnt=#r, rows=r}
end

function getmeta(sql)
	local stmt = db.prepare(sql)
	return {colmetas=stmt:column_info(), bindcnt=stmt:bind_param_cnt()}
end

--function constructor()
--	db.exec("create table sample(num int, data text)")
--end

function constructor()
  db.exec([[CREATE TABLE IF NOT EXISTS CUST_GAS_USAGE_TBL(
        DEVICE_ID text,
        SUPPLIER text,
        RSSI text,
        BATTERY text,
        STATUS text,
        DATETIME text,
        TOTAL_FLOW text,
        FLOW_RATE text,
        PRSESSURE text
    )]])

	db.exec([[CREATE TABLE IF NOT EXISTS CUST_INFO_TBL(
				USER text,
				ADDRESS text,
				DEVICE_TYPE text,
				DEVICE_ID text,
				TX_ID text,
	      DATETIME text
	  )]])

	db.exec([[CREATE TABLE IF NOT EXISTS CUST_SUBSCRIBE_TBL(
				TO_USER text,
				FROM_USER text,
				EMAIL text,
				REWARD number,
				DEVICE_TYPE text,
				DEVICE_ID text,
				TX_ID text,
				STATUS text,
		    DATETIME text
	  )]])

	db.exec([[CREATE TABLE IF NOT EXISTS CUST_SUBSCRIBE_HIST_TBL(
				TO_USER text,
				FROM_USER text,
				EMAIL text,
				AMOUNT number,
				DEVICE_TYPE text,
				DEVICE_ID text,
				TX_ID text,
				STATUS text,
		    DATETIME text
	  )]])

	db.exec([[CREATE TABLE IF NOT EXISTS CUST_BILLS_HIST_TBL(
				TO_USER text,
				FROM_USER text,
				AMOUNT number,
		    DATETIME text
	  )]])
end

-- insert a row to the CUST_GAS_USAGE_TBL
function insert_cust_gas_usage_tbl(device_id, supplier, rssi, battery, status, datetime, total_flow, flow_rate, prsessure)
  stmt = db.prepare("INSERT INTO CUST_GAS_USAGE VALUES (?,?,?,?,?,?,?,?,?)")
  stmt:exec(device_id, supplier, rssi, battery, status, datetime, total_flow, flow_rate, prsessure)
end

-- insert a row to the CUST_INFO_TBL
function insert_cust_info_tbl(user, address, device_type, device_id, tx_id, datetime)
  stmt = db.prepare("INSERT INTO CUSTOMER_INFO_TBL VALUES (?,?,?,?,?,?)")
  stmt:exec(user, address, device_type, device_id, tx_id, datetime)
end

-- insert1 a row to the CUST_SUBSCRIBE_TBL
function insert_cust_subscribe_tbl(to_user, from_user, email, reward, device_type, device_id, tx_id, status, datetime)
  stmt = db.prepare("INSERT INTO CUST_SUBSCRIBE_TBL VALUES (?,?,?,?,?,?,?,?,?)")
  stmt:exec(to_user, from_user, email, reward, device_type, device_id, tx_id, status, datetime)
end


abi.register(exec,insert_cust_gas_usage_tbl,insert_cust_info_tbl,insert_cust_subscribe_tbl)
abi.register_view(version, query_begin, query_next, getmeta)
