import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import { registerVoteItems } from '@/api/vote';
import useVote from '@/hooks/vote/useGetVote';
import { VoteHandler } from '@/mock';
import { theme } from '@/styles/Theme';
import { ThemeProvider } from '@emotion/react';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...VoteHandler);

describe('useGetVote 테스트', () => {
	beforeAll(() => server.listen());
	afterEach(() => server.resetHandlers());
	afterAll(() => server.close());

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

	test('투표를 조회할수 있다.', async () => {
		await registerVoteItems({
			articleId: '0',
			items: ['1', '2', '3'],
			expiryDate: '2022-08-13T13:34',
		});

		const { result, waitFor } = renderHook(() => useVote('0'), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		const { data } = result.current;
		if (typeof data === 'undefined') {
			return;
		}
		expect(data.voteItems.map((voteItem) => voteItem.content)).toEqual(['1', '2', '3']);
	});
});
