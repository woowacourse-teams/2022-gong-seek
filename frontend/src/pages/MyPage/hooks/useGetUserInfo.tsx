import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserInfo } from '@/api/myPage';
import { Author } from '@/types/author';

const useGetUserInfo = () => {
    const {data, isSuccess, isError, isLoading, isIdle, error} = useQuery<Author, Error>('user-info', getUserInfo);

    useEffect(() => {
        if(isError){
            throw new Error(error.message);
        }
    }, [isError]);
    
  return {
    data, isSuccess, isLoading, isIdle
  }
}

export default useGetUserInfo;