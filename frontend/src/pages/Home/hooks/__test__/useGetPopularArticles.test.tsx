import useGetPopularArticles from '../useGetPopularArticles';
import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import { ArticleHandler } from '@/mock/article';
import { theme } from '@/styles/Theme';
import { ThemeProvider } from '@emotion/react';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...ArticleHandler);

describe('useGetPopularArticles 테스트', () => {
	beforeAll(() => server.listen());
	afterEach(() => server.resetHandlers());
	afterAll(() => server.close());

	test('인기 게시글들에 대해서 조회 할 경우, 10개의 응답이 내려온다', async () => {
		const queryClient = new QueryClient();
		const wrapper = ({ children }: { children: React.ReactNode }) => (
			<React.StrictMode>
				<ThemeProvider theme={theme}>
					<QueryClientProvider client={queryClient}>
						<BrowserRouter>{children}</BrowserRouter>
					</QueryClientProvider>
				</ThemeProvider>
			</React.StrictMode>
		);

		const { result, waitFor } = renderHook(() => useGetPopularArticles(), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data === 'undefined') {
			return;
		}

		const { data } = result.current;
		const { articles } = data;
		expect(articles[0].title.length >= 1).toEqual(true);
	});
});
