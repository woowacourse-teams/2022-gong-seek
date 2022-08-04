import { ThemeProvider, Global } from '@emotion/react';
import { RecoilRoot } from 'recoil';
import { QueryClient, QueryClientProvider } from 'react-query';
import { initialize, mswDecorator } from 'msw-storybook-addon';

import { BrowserRouter } from 'react-router-dom';
import { theme } from '@/styles/Theme';
import { reset } from '@/styles/reset';

const queryClient = new QueryClient();
export const parameters = {
	actions: { argTypesRegex: '^on[A-Z].*' },
	controls: {
		matchers: {
			color: /(background|color)$/i,
			date: /Date$/,
		},
	},
};

export const decorators = [
	mswDecorator,
	(Story) => (
		<ThemeProvider theme={theme}>
			<Global styles={reset} />
			<QueryClientProvider client={queryClient}>
				<RecoilRoot>
					<BrowserRouter>
						<Story />
					</BrowserRouter>
				</RecoilRoot>
			</QueryClientProvider>
		</ThemeProvider>
	),
];
initialize();
