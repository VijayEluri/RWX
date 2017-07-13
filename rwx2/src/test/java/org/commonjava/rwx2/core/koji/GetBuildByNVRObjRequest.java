package org.commonjava.rwx2.core.koji;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by ruhan on 7/19/17.
 */
@Request( method="getBuild" )
public class GetBuildByNVRObjRequest
{
    @DataIndex( 0 )
    private KojiNVR nvr;

    public GetBuildByNVRObjRequest( KojiNVR nvr )
    {
        this.nvr = nvr;
    }

    public KojiNVR getNvr(){
        return nvr;
    }
}
