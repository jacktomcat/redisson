/**
 * Copyright 2016 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.mapreduce;

import org.redisson.api.RObject;
import org.redisson.api.RedissonClient;
import org.redisson.api.mapreduce.RCollectionMapReduce;
import org.redisson.api.mapreduce.RCollectionMapper;
import org.redisson.api.mapreduce.RReducer;
import org.redisson.connection.ConnectionManager;

/**
 * 
 * @author Nikita Koksharov
 *
 * @param <VIn> input value type
 * @param <KOut> output key type
 * @param <VOut> output value type
 */
public class RedissonCollectionMapReduce<VIn, KOut, VOut> extends MapReduceExecutor<RCollectionMapper<VIn, KOut, VOut>, VIn, KOut, VOut> 
                                                            implements RCollectionMapReduce<VIn, KOut, VOut> {

    public RedissonCollectionMapReduce(RObject object, RedissonClient redisson, ConnectionManager connectionManager) {
        super(object, redisson, connectionManager);
    }
    
    @Override
    public RCollectionMapReduce<VIn, KOut, VOut> mapper(RCollectionMapper<VIn, KOut, VOut> mapper) {
        check(mapper);
        this.mapper = mapper;
        return this;
    }

    @Override
    public RCollectionMapReduce<VIn, KOut, VOut> reducer(RReducer<KOut, VOut> reducer) {
        check(reducer);
        this.reducer = reducer;
        return this;
    }
    
    @Override
    protected CollectionMapperTask<VIn, KOut, VOut> createTask(String resultMapName) {
        return new CollectionMapperTask<VIn, KOut, VOut>(mapper, reducer, objectName, semaphoreName, resultMapName, objectCodec.getClass(), objectClass);
    }

}
