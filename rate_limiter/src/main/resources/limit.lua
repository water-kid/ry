-- 存在redis中的key
local key = KEYS[1]
local time = tonumber(ARGV[1])
local count = tonumber(ARGV[2])

print(key,time,count)

local current = redis.call("get",key)

--
---- 超过限流
if current and tonumber(current)>count then
    return tonumber(current)
end

-- 没有超过  +1
current = redis.call("incr",key)


-- 之前没有用过，设置key的过期时间
if tonumber(current) == 1 then
    redis.call("expire",key,time)
end

-- 之前设置过
return tonumber(current)



