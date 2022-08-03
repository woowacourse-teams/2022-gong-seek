import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import { ArticleHandler } from '@/mock';
import useGetCategoryArticles from '@/pages/CategoryArticles/hooks/useGetCategoryArticles';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...ArticleHandler);

describe('useGetCategoryArticles 테스트', () => {
	beforeAll(() => server.listen());
	afterEach(() => server.resetHandlers());
	afterAll(() => server.close());

	const queryClient = new QueryClient();
	const wrapper = ({ children }: { children: React.ReactNode }) => (
		<React.StrictMode>
			<QueryClientProvider client={queryClient}>
				<BrowserRouter>{children}</BrowserRouter>
			</QueryClientProvider>
		</React.StrictMode>
	);

	test('질문 카테고리에 대해서 조회 할 수 있다', async () => {
		const { result, waitFor } = renderHook(() => useGetCategoryArticles('question'), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		const { data } = result.current;
		if (typeof data === 'undefined') {
			return;
		}
		expect(data.pages[0].articles[0].category).toEqual('question');
	});

	test('토론 카테고리에 대해서 조회 할 수 있다', async () => {
		const { result, waitFor } = renderHook(() => useGetCategoryArticles('discussion'), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		const { data } = result.current;
		if (typeof data === 'undefined') {
			return;
		}
		expect(data.pages[0].articles[0].category).toEqual('discussion');
	});
});
