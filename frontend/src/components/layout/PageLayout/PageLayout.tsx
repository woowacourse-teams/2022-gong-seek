import { Size } from '@emotion/react';
import styled from '@emotion/styled';

const PageLayout = styled.section<Size>`
	display: ${({ display }) => display || 'flex'};
	justify-content: ${({ justifyContent }) => justifyContent || 'center'};
	align-items: ${({ alignItems }) => alignItems || 'center'};
	gap: ${({ gap }) => gap};
	flex-direction: ${({ flexDirection }) => flexDirection};
	width: ${({ width }) => width || 'fit-content'};
	height: ${({ height }) => height || 'fit-content'};
	padding: ${({ padding }) => padding || 0};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.secondary};
`;

export default PageLayout;
