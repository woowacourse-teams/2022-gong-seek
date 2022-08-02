import { getSearchResult } from '@/api/search'
import { InfiniteSearchResultType } from '@/types/searchResponse'
import  { useEffect } from 'react'
import { useInfiniteQuery} from 'react-query'

const useGetSearch = (target: string) => {
    const cursorId = '';
    const {data, isSuccess, isLoading, isError, isIdle, error, refetch, fetchNextPage} = useInfiniteQuery<InfiniteSearchResultType , Error>('search-result', () => getSearchResult({target, cursorId}),
    {
        getNextPageParam: (lastPage) => {
            const {hasNext, articles} = lastPage;

            if(hasNext && articles.length >= 1){
                const lastCursorId = articles[articles.length - 1].id;
                return{
                    articles,
                    hasNext,
                    lastCursorId,
                }
            }
            return undefined;
        }
    })

    useEffect(() => {
        if(isError){
            throw new Error(error.message);
        }
    },[isError]);

    return {
        data, 
        isSuccess, 
        isLoading,
        isIdle,
        refetch,
        fetchNextPage,
    }
}

export default useGetSearch;