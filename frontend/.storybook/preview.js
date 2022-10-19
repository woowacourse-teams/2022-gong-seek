import { initialize, mswDecorator } from 'msw-storybook-addon';
import { QueryClient, QueryClientProvider } from 'react-query';
import { RecoilRoot } from 'recoil';

import { theme } from '@/styles/Theme';
import { reset } from '@/styles/reset';
import { ThemeProvider, Global } from '@emotion/react';

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
					<Story />
				</RecoilRoot>
			</QueryClientProvider>
		</ThemeProvider>
	),
];
initialize();
