import { QueryClient, QueryClientProvider } from 'react-query';
import { renderHook } from '@testing-library/react-hooks';
import React from 'react';
import useGetPopularArticles from '../useGetPopularArticles';
import { setupServer } from 'msw/node'

import { ArticleHandler } from '@/mock/article';
import { BrowserRouter } from 'react-router-dom';

const server = setupServer(...ArticleHandler);

describe('useGetPopularArticles 테스트', () => {
    beforeAll(() => server.listen())
    afterEach(() => server.resetHandlers())
    afterAll(() => server.close())

    test('인기 게시글들에 대해서 조회 할 수 있다', async () => {

        const queryClient = new QueryClient();
        const wrapper = ({ children }: { children: React.ReactNode }) => (
            <React.StrictMode>
                <QueryClientProvider client={queryClient}>
                    <BrowserRouter>
                        {children}
                    </BrowserRouter>
                </QueryClientProvider>
            </React.StrictMode>
        );
    
        const { result, waitFor } = renderHook(() => useGetPopularArticles(), { wrapper });
        await waitFor(() => result.current.isSuccess, {interval: 100});
        if(typeof result.current.data === 'undefined'){
            return;
        }
        
        const {data} = result.current;
        const {articles} =data;
        expect(articles.length).toEqual(10);
    })  
})
