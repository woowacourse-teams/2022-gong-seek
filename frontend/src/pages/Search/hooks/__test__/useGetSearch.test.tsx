import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import { SearchHandler } from '@/mock';
import useGetSearch from '@/pages/Search/hooks/useGetSearch';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...SearchHandler);

describe('검색이 작동되는지에 대해서 테스트 한다', () => {
	beforeAll(() => server.listen());
	afterEach(() => server.resetHandlers());
	afterAll(() => server.close());

	const queryClient = new QueryClient();
	const wrapper = ({ children }: { children: React.ReactNode }) => (
		<React.StrictMode>
			<QueryClientProvider client={queryClient}>
				<RecoilRoot>
					<BrowserRouter>{children}</BrowserRouter>
				</RecoilRoot>
			</QueryClientProvider>
		</React.StrictMode>
	);

	test('검색한 검색어가 포함된 결과들을 조회 할 수 있다', async () => {
		const { result, waitFor } = renderHook(() => useGetSearch('hi'), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data === 'undefined') {
			return;
		}

		const { data } = result.current;
		expect(data.pages[0].articles[0].title.includes('hi')).toEqual(true);
	});
});
