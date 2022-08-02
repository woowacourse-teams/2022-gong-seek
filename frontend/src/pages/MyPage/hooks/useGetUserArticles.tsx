import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserArticles } from '@/api/myPage';
import { UserArticlesResponse } from '@/types/articleResponse';

const useGetUserArticles = () => {
  const {data, isSuccess, isLoading, isError, isIdle, error} = useQuery<UserArticlesResponse, Error>('user-articles', getUserArticles);

  useEffect(() => {
      if(isError){
          throw new Error(error.message);
      }
  }, [isError]);

  return  {
    data, isSuccess, isLoading, isIdle
  }
}

export default useGetUserArticles;